using HR.SSS.R3.Constants;
using HR.SSS.R3.Extractors.Abstracts;
using HR.SSS.R3.Extractors.Interfaces;
using System.Text;

namespace HR.SSS.R3.Extractors.EmployeeList.FieldTransformers
{
    class SssNumberTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string SssNumber;

        public SssNumberTransformer(string sssNumber) : base(sssNumber)
        {
            this.SssNumber = sssNumber;
        }

        public string TransformField()
        {
            if (this.SssNumber == null)
            {
                return "               ";
            }

            // Convert to uppercase
            string sssNumberFinal = this.SssNumber.Length == 9 ? $"0{ this.SssNumber }" : this.SssNumber; 
            sssNumberFinal = sssNumberFinal.Insert(2, "-").Insert(10, "-");
            var sssNumberFieldLength = sssNumberFinal.Length;

            // Truncate if more than max length
            if (sssNumberFieldLength > OutputFileConstants.SssNumberMaxLength)
            {
                sssNumberFinal = sssNumberFinal.Substring(0, OutputFileConstants.SssNumberMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (sssNumberFieldLength < OutputFileConstants.SssNumberMaxLength)
            {
                int difference = OutputFileConstants.SssNumberMaxLength - sssNumberFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(sssNumberFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                sssNumberFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ sssNumberFinal } ";
        }
    }
}
