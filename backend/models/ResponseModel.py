class RagResponse:
    def __init__(self, question, answer) -> None:
        self.question = question
        self.answer = answer
        pass
class RAGASResult:
    def __init__(
        self,
        context_precision,
        context_recall,
        faithfulness,
        answer_relevancy,
    ) -> None:
        self.context_precision = context_precision
        self.context_recall = context_recall
        self.faithfulness = faithfulness
        self.answer_relevancy = answer_relevancy
        pass
