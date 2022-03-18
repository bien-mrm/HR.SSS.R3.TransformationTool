using System.Collections.Generic;

namespace HR.SSS.R3.Models
{
    public class R3SessionContainer
    {
        public string InputFile { get; set; }

        public string InputSheetName { get; set; }

        public bool IsHeaderPresent { get; set; }

        public string InputDirectory { get; set; }

        public string OutputFileName { get; set; }

        public string EmployerName { get; set; }

        public string EmployerNumber { get; set; }

        public string ApplicablePeriod { get; set; }

        public List<R3Record> R3Records { get; set; }

    }
}
