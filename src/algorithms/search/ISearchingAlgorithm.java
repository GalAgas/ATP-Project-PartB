package algorithms.search;

public interface ISearchingAlgorithm
{
    public String getName();
    public Solution solve(ISearchable s);
    public int getNumberOfNodesEvaluated();
}
