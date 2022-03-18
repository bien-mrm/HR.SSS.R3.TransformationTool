using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

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
