from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_community.document_loaders import Docx2txtLoader
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_community.vectorstores import Chroma
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnablePassthrough
from langchain_core.output_parsers import StrOutputParser
from operator import itemgetter
from dotenv import load_dotenv
from langchain_huggingface import HuggingFaceEmbeddings


load_dotenv()
class RagOperations:

    def __init__(self) -> None:
        pass

    def load_data(self, fileLoc):
        loader = Docx2txtLoader(fileLoc)
        documents = loader.load()
        return documents

    def return_chunks(self, documents):
        text_splitter = RecursiveCharacterTextSplitter(chunk_size=800, chunk_overlap=80)
        texts = text_splitter.split_documents(documents)
        return texts

    def build_chain(self, store):
        llm = ChatGoogleGenerativeAI(model="gemini-2.0-flash", temperature=0)
        retriever = store.as_retriever()

        prompt = ChatPromptTemplate.from_template("""
            Answer the question based only on the following context.
            If you cannot answer the question with the context, respond with 'I don't know':

            Context: {context}
            Question: {question}
            """
        )

        chain = (
            {"context": itemgetter("question") | retriever, "question": itemgetter("question")}
            | RunnablePassthrough.assign(context=itemgetter("context"))
            | {"response": prompt | llm | StrOutputParser(), "context": itemgetter("context")}
        )
        return chain

    # def return_llm_chain(self, fileLoc):
    #     documents = self.load_data(fileLoc)
    #     chunks = self.return_chunks(documents)
    #     chain = self.return_chain(chunks)
    #     return chain
    
    def build_vector_store(self, texts):
        embeddings = HuggingFaceEmbeddings(
            model_name="sentence-transformers/all-MiniLM-L6-v2"
        )
        store = Chroma.from_documents(
            texts, embeddings, collection_name="challenge_document"
        )
        return store
    
    
    def get_rag_response_chain(self, fileLoc):
        documents = self.load_data(fileLoc)
        chunks = self.return_chunks(documents)
        store = self.build_vector_store(chunks)
        return self.build_chain(store)

    def get_rag_response(self, question, chain):
        result = chain.invoke({"question": question})
        return {"question": question, "answer": result["response"]}