from models import ResponseModel
from langchain import OpenAI
from langchain.document_loaders import TextLoader, PyPDFLoader
from langchain_community.document_loaders import Docx2txtLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.vectorstores import Chroma
from langchain.chains import RetrievalQA
from dotenv import load_dotenv


class RagOperations:

    def __init__(self) -> None:
        load_dotenv()
        pass

    def load_data(self, fileLoc):
        loader = Docx2txtLoader(fileLoc)
        documents = loader.load()
        return documents

    def return_chunks(self, documents):
        text_splitter = RecursiveCharacterTextSplitter(chunk_size=150, chunk_overlap=30)
        texts = text_splitter.split_documents(documents)
        return texts

    def return_chain(self, texts):
        embeddings = OpenAIEmbeddings()
        store = Chroma.from_documents(
            texts, embeddings, collection_name="challenge_document"
        )
        llm = OpenAI(temperature=0)
        retriever=store.as_retriever()
        return RetrievalQA.from_chain_type(llm, retriever=retriever)

    # def return_llm_chain(self, fileLoc):
    #     documents = self.load_data(fileLoc)
    #     chunks = self.return_chunks(documents)
    #     chain = self.return_chain(chunks)
    #     return chain
    
    def get_rag_response(self, question, fileLoc):
        documents = self.load_data(fileLoc)
        chunks = self.return_chunks(documents)
        chain = self.return_chain(chunks)
        response = chain.run(question)
        return ResponseModel.RagResponse(question, response)
