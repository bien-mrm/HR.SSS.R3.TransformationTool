using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    public class ELEmployerNumberTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string OutputFileName;

        public ELEmployerNumberTransformer(string outputFileName) : base(outputFileName)
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
