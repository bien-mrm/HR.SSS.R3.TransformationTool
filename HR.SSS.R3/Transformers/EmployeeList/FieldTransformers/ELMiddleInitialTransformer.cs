using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELMiddleInitialTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string MiddleInitial;

        public ELMiddleInitialTransformer(string middleInitial) : base(middleInitial)
        {
            this.MiddleInitial = middleInitial;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string middleInitialFinal = !String.IsNullOrEmpty(this.MiddleInitial) ? this.MiddleInitial.ReplaceNcharacters().ToUpper() : "";

            // Truncate if more than max length
            if (middleInitialFinal.Length > EmployeeListConstants.MiddleInitialMaxLength)
            {
                middleInitialFinal = middleInitialFinal.Substring(0, EmployeeListConstants.MiddleInitialMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (middleInitialFinal.Length < EmployeeListConstants.MiddleInitialMaxLength)
            {
                int difference = EmployeeListConstants.MiddleInitialMaxLength - middleInitialFinal.Length;
                middleInitialFinal = middleInitialFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ middleInitialFinal } ";
        }
    }
}
