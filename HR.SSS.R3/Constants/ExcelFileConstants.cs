namespace HR.SSS.R3.Constants
{
    public static class ExcelFileConstants
    {
        // I know, enum can be used here but casting is not rendered real-time 
        // under worksheet.Cells[r, (int) ExcelFileConstants.FamilyName].Value.ToString();
        // Enums cannot be static

        public static int FamilyName => 1;

        public static int GivenName => 2;

        public static int MiddleInitial => 2;

        public static int SssNumber => 3;

        public static int SssContribution => 4;

        public static int EcAmount => 5;

        public static int Remark => 6;

        public static int DateHired => 7;
    }
}
