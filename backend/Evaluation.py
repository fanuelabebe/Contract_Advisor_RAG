from datasets import Dataset
from ragas import evaluate
from ragas.metrics import (
    faithfulness,
    answer_relevancy,
    context_recall,
    context_precision,
)


class Evaluation:

    def get_dataset(questions, ground_truths, rag_chain, retriever):
        answers = []
        contexts = []
        for query in questions:
            answers.append(rag_chain.invoke(query))
            contexts.append(
                [docs.page_content for docs in retriever.get_relevant_documents(query)]
            )
        data = {
            "question": questions,
            "answer": answers,
            "contexts": contexts,
            "ground_truths": ground_truths,
        }

        return Dataset.from_dict(data)

    def evaluate_RAG(dataset):
        result = evaluate(
            dataset=dataset,
            metrics=[
                context_precision,
                context_recall,
                faithfulness,
                answer_relevancy,
            ],
        )
        return result
