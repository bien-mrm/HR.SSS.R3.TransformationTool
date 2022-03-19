using HR.SSS.R3.Extractors.Abstracts;
using HR.SSS.R3.Extractors.Interfaces;

namespace HR.SSS.R3.Extractors.EmployeeList.FieldTransformers
{
    public class EmployerNumberTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string OutputFileName;

        public EmployerNumberTransformer(string outputFileName) : base(outputFileName)
        {
            this.OutputFileName = outputFileName;
        }

        public string TransformField()
        {
            string code = $"[{ this.OutputFileName }]";

            return code;
        }
    }
}
