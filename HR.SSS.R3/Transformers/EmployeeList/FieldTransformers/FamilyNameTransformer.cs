using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class FamilyNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string FamilyName;

        public FamilyNameTransformer(string familyName) : base(familyName)
        {
            this.FamilyName = familyName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string familyNameFinal = this.FamilyName.ToUpper();
            var familyNameFieldLength = this.FamilyName.Length;


            // Truncate if more than max length
            if (familyNameFieldLength > OutputFileConstants.FamilyNameMaxLength)
            {
                familyNameFinal = familyNameFinal.Substring(0, OutputFileConstants.FamilyNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (familyNameFieldLength < OutputFileConstants.FamilyNameMaxLength)
            {
                int difference = OutputFileConstants.FamilyNameMaxLength - familyNameFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(familyNameFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                familyNameFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ familyNameFinal.ReplaceNcharacters() } ";
        }
    }
}
