using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

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
            string middleInitialFinal = this.MiddleInitial != null ? this.MiddleInitial.ToUpper() : "";
            var middleInitialFieldLength = this.MiddleInitial != null ? this.MiddleInitial.Length : 0;


            // Truncate if more than max length
            if (middleInitialFieldLength > EmployeeListConstants.MiddleInitialMaxLength)
            {
                middleInitialFinal = middleInitialFinal.Substring(0, EmployeeListConstants.MiddleInitialMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (middleInitialFieldLength < EmployeeListConstants.MiddleInitialMaxLength)
            {
                int difference = EmployeeListConstants.MiddleInitialMaxLength - middleInitialFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(middleInitialFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                middleInitialFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ middleInitialFinal.ReplaceNcharacters() } ";
        }
    }
}
