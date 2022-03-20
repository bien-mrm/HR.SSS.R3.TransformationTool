using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class R3MiddleInitialTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string MiddleInitial;

        public R3MiddleInitialTransformer(string middleInitial) : base(middleInitial)
        {
            this.MiddleInitial = middleInitial;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string middleInitialFinal = !String.IsNullOrEmpty(this.MiddleInitial) ? 
                this.MiddleInitial.Substring(0, R3OutputFileConstants.MiddleInitialMaxLength).ReplaceNcharacters().ToUpper() : " ";

            return middleInitialFinal;
        }
    }
}
