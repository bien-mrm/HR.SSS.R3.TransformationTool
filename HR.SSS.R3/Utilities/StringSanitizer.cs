namespace HR.SSS.R3.Utilities
{
    public static class StringSanitizer
    {
        public static string ReplaceNcharacters(this string str)
        {
            return str.Replace("Ñ", "N");
        }
    }
}
