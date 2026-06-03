import os
import logging
from contextlib import asynccontextmanager
from fastapi import FastAPI, Request, HTTPException
from typing import List
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware
from utils import scripts
from Evaluation import Evaluation

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


@asynccontextmanager
async def lifespan(app: FastAPI):
    logger.info("Building RAG chain on startup...")
    rag_ops = scripts.RagOperations()
    file_path = os.getenv("CONTRACT_FILE_PATH", "./data/raptor_contract.docx")
    app.state.chain = rag_ops.get_rag_response_chain(file_path)
    logger.info("RAG chain ready.")
    yield
    logger.info("Shutting down.")


app = FastAPI(lifespan=lifespan)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000", "http://localhost:8080"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


class RagResponseBase(BaseModel):
    question: str
    answer: str


class RAGASResponseBase(BaseModel):
    context_precision: str
    context_recall: str
    faithfulness: str
    answer_relevancy: str


class QuestionRequest(BaseModel):
    question: str

@app.post("/getanswer", response_model=RagResponseBase)
async def return_answer(body: QuestionRequest, request: Request):
    question = body.question
    if not question or not question.strip():
        raise HTTPException(status_code=400, detail="Question cannot be empty.")
    if len(question) > 500:
        raise HTTPException(status_code=400, detail="Question too long. Max 500 characters.")
    try:
        chain = request.app.state.chain
        rag_ops = scripts.RagOperations()
        result = rag_ops.get_rag_response(question=question, chain=chain)
        return result
    except AttributeError:
        logger.error("RAG chain not initialized.")
        raise HTTPException(status_code=503, detail="Service not ready. Try again shortly.")
    except Exception as e:
        logger.error(f"Error in /getanswer: {e}")
        raise HTTPException(status_code=500, detail="Failed to generate answer.")

@app.get("/health")
async def health_check(request: Request):
    chain_ready = hasattr(request.app.state, "chain") and request.app.state.chain is not None
    return {"status": "ok" if chain_ready else "degraded", "chain_ready": chain_ready}

@app.get("/getevaluation", response_model=List[RAGASResponseBase])
async def return_evaluation(type: int):
    if type == 1:
        file_path = os.getenv("ADVISORY_FILE_PATH", "./data/robinson_advisory.docx")
    else:
        file_path = os.getenv("CONTRACT_FILE_PATH", "./data/raptor_contract.docx")
    evaluation = Evaluation()
    return [evaluation.return_eval_response("")]