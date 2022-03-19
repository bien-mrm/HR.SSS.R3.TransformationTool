using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace HR.SSS.R3.Utilities
{
	static class SplitCamelCaseExtension
	{
		public static string SplitCamelCase(this string str)
		{
			if (str.Contains(" ")) return str;

			return Regex.Replace(Regex.Replace(str, @"(\P{Ll})(\P{Ll}\p{Ll})", "$1 $2"), @"(\p{Ll})(\P{Ll})", "$1-$2");
		}
	}

}
