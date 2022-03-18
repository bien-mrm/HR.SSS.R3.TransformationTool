using HR.SSS.R3.Models;

namespace HR.SSS.R3.Utilities
{
    public static class RandomCodeGenerator
    {
        public static string AssignCode(R3SessionContainer r3Session)
        {
            var random = Randomizer.RandomString(8);
            return $"R3{ r3Session.EmployerNumber + r3Session.ApplicablePeriod }.{ random }";
        }
    }
}
