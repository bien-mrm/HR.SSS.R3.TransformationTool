using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;

namespace HR.SSS.R3.Processors.FieldTransformers
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
