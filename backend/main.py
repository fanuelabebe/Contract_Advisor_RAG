from fastapi import FastAPI, HTTPException, Depends
from typing import Annotated, List
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware
from utils import scripts
from Evaluation import Evaluation


app = FastAPI()

# origins = ["http://localhost:3000"]

# app.add_middleware(
#     CORSMiddleware,
#     allow_origins=origins,
# )


class RagResponseBase(BaseModel):
    question: str
    answer: str


class RAGASResponseBase(BaseModel):
    context_precision: str
    context_recall: str
    faithfulness: str
    answer_relevancy: str


@app.get("/getanswer", response_model=RagResponseBase)
async def return_answer(question: str):
    ragOperation = scripts.RagOperations()
    result = ragOperation.get_rag_response(
        question=question, fileLoc="./data/raptor_contract.docx"
    )
    return result


@app.get("/getevaluation", response_model=List[RAGASResponseBase])
async def return_answer(type: int):

    if type == 1:
        filePath = "./data/robinson_advisory.docx"
    elif type == 2:
        filePath = "./data/raptor_contract.docx"

    # ragOperation = scripts.RagOperations()
    # chain_retriever_tuple = ragOperation.return_llm_chain(fileLoc=filePath)
    # retriever = chain_retriever_tuple[0]
    # chain = chain_retriever_tuple[1]
    evaluation = Evaluation()
    # dataset = evaluation.get_dataset(
    #     evaluation.questions, evaluation.ground_truths, chain, retriever, 0
    # )
    # results = await evaluation.evaluate_RAG(dataset)
    return [evaluation.return_eval_response("")]
