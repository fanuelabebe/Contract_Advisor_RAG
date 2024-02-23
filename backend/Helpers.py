from flashrank.Ranker import Ranker, RerankRequest


def get_result(query, passages, choice):
    if choice == "Nano":
        ranker = Ranker()
    elif choice == "Small":
        ranker = Ranker(model_name="ms-marco-MiniLM-L-12-v2", cache_dir="/opt")
    elif choice == "Medium":
        ranker = Ranker(model_name="rank-T5-flan", cache_dir="/opt")
    elif choice == "Large":
        ranker = Ranker(model_name="ms-marco-MultiBERT-L-12", cache_dir="/opt")
    rerankerrequest = RerankRequest(query=query, passages=passages)
    results = ranker.rerank(rerankerrequest)
    return results
