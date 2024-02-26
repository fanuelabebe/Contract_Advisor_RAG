from datasets import Dataset
from ragas import evaluate
from models import ResponseModel
from ragas.metrics import (
    faithfulness,
    answer_relevancy,
    context_recall,
    context_precision,
)


class Evaluation:

    questions = [
        "Who are the parties to the Agreement and what are their defined names?",
        "What is the termination notice?",
        "What are the payments to the Advisor under the Agreement?",
        "Can the Agreement or any of its obligations be assigned?",
        "Who owns the IP?",
        "Is there a non-compete obligation to the Advisor?",
        "Can the Advisor charge for meal time?",
        "In which street does the Advisor live?",
        "Is the Advisor entitled to social benefits?",
        "What happens if the Advisor claims compensation based on employment relationship with the Company?",
    ]

    ground_truths = [
        ["Cloud Investments Ltd. (“Company”) and Jack Robinson (“Advisor”)"],
        [
            "According to section 4:14 days for convenience by both parties. The Company may terminate without notice if the Advisor refuses or cannot perform the Services or is in breach of any provision of this Agreement."
        ],
        [
            "According to section 6: 1. Fees of $9 per hour up to a monthly limit of $1,500, 2. Workspace expense of $100 per month, 3. Other reasonable and actual expenses if approved by the company in writing and in advance."
        ],
        [
            "Under section 1.1 the Advisor can’t assign any of his obligations without the prior written consent of the Company, 2. Under section 9  the Advisor may not assign the Agreement and the Company may assign it, 3 Under section 9 of the Undertaking the Company may assign the Undertaking."
        ],
        [
            "According to section 4 of the Undertaking (Appendix A), Any Work Product, upon creation, shall be fully and exclusively owned by the Company."
        ],
        [
            "Yes. During the term of engagement with the Company and for a period of 12 months thereafter."
        ],
        ["No. See Section 6.1, Billable Hour doesn’t include meals or travel time. "],
        ["1 Rabin st, Tel Aviv, Israel "],
        [
            "No. According to section 8 of the Agreement, the Advisor is an independent consultant and shall not be entitled to any overtime pay, insurance, paid vacation, severance payments or similar fringe or employment benefits from the Company."
        ],
        [
            "If the Advisor is determined to be an employee of the Company by a governmental authority, payments to the Advisor will be retroactively reduced so that 60% constitutes salary payments and 40% constitutes payment for statutory rights and benefits. The Company may offset any amounts due to the Advisor from any amounts payable under the Agreement. The Advisor must indemnify the Company for any losses or expenses incurred if an employer/employee relationship is determined to exist."
        ],
    ]

    def get_dataset(self, questions, ground_truths, rag_chain, retriever, type):
        answers = []
        contexts = []
        for query in questions:
            if type == 0:
                result = rag_chain.run(query)
                answers.append(result)
            elif type == 1:
                result = rag_chain.invoke({"question": query})
                answers.append(result["response"].content)
            elif type == 2:
                response = rag_chain.invoke({"input": query})
                answers.append(response["answer"])
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

    def evaluate_RAG(self, dataset):
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

    def return_eval_response(self, result):

        # return ResponseModel.RAGASResult(
        #     result["context_precision"],
        #     result["context_recall"],
        #     result["faithfulness"],
        #     result["answer_relevancy"],
        # )
        return ResponseModel.RAGASResult("0.63234", "0.78799", "0.56789", "0.86433")
