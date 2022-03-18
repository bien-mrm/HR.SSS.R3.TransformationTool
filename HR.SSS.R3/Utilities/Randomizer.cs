using System;
using System.Linq;

namespace HR.SSS.R3.Utilities
{
    public static class Randomizer
    {
        private static Random random = new Random();

        public static string RandomString(int length)
        {
            const string chars = "0123456789";
            return new string(Enumerable.Repeat(chars, length)
                .Select(s => s[random.Next(s.Length)]).ToArray());
        }
    }
}
