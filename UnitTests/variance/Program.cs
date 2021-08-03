using System;
using System.Linq;

namespace variance
{
    class Program
    {
        static void Main(string[] args) => System.Console.WriteLine(args.Length switch
        {
        0 => "입력된 데이터가 없습니다.",
        1 => "데이터가 부족해 분산을 계산할 수 없습니다",
        _ => GetVarianceOutput(args)
        });

        private static string GetVarianceOutput(string[] args)
        {
        double[] source = ParseArguments(args);
        double mean = CalculateMean(source);
        double sumOfSquares = CalculateSumOfSquares(source, mean);
        double variance = sumOfSquares / (args.Length - 1);
        string output = $"분산: {variance}";
        return output;
        }

        private static double CalculateSumOfSquares(double[] source, double mean)
        {
            return source.Select(x => x - mean)
                        .Select(x => x * x)
                        .Sum();
        }

        private static double CalculateMean(double[] source) => source.Average();

        private static double[] ParseArguments(string[] args) => args.Select(double.Parse).ToArray();
  }
}

// 09:23