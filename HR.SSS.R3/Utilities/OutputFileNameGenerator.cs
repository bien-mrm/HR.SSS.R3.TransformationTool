using HR.SSS.R3.Models;
using System;

namespace HR.SSS.R3.Utilities
{
    public static class OutputFileNameGenerator
    {
        public static string AssignCode(R3SessionContainer r3Session)
        {
            var now = DateTime.Now.ToString("MMddyyHHmm");
            return $"R3{ r3Session.EmployerNumber + r3Session.ApplicablePeriod }.{ now }";
        }
    }
}
