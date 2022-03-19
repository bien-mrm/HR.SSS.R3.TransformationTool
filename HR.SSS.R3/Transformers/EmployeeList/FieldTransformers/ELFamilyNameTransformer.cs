using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELFamilyNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string FamilyName;

        public ELFamilyNameTransformer(string familyName) : base(familyName)
        {
            this.FamilyName = familyName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string familyNameFinal = this.FamilyName.ReplaceNcharacters().SplitCamelCase().ToUpper();
            var familyNameFieldLength = familyNameFinal.Length;


            // Truncate if more than max length
            if (familyNameFieldLength > EmployeeListConstants.FamilyNameMaxLength)
            {
                familyNameFinal = familyNameFinal.Substring(0, EmployeeListConstants.FamilyNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (familyNameFieldLength < EmployeeListConstants.FamilyNameMaxLength)
            {
                int difference = EmployeeListConstants.FamilyNameMaxLength - familyNameFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(familyNameFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                familyNameFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ familyNameFinal } ";
        }
    }
}
