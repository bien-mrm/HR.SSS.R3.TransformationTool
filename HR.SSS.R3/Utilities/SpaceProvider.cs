using System.Text;

namespace HR.SSS.R3.Utilities
{
    public static class SpaceProvider
    {
        public static string AddSpace(this string str, int numberOfSpaces)
        {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < numberOfSpaces; i++)
            {
                sb.Append(" ");
            }

            return $"{ str }{ sb.ToString() }";
        }

        public static string AddSpace(int numberOfSpaces)
        {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < numberOfSpaces; i++)
            {
                sb.Append(" ");
            }

            return sb.ToString();
        }
    }
}
