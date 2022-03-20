using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class R3GivenNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string GivenName;

        public R3GivenNameTransformer(string givenName) : base(givenName)
        {
            this.GivenName = givenName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string givenNameFinal = this.GivenName.ReplaceNcharacters().SplitCamelCase().ToUpper();
            var givenNameFieldLength = this.GivenName.Length;


            // Truncate if more than max length
            if (givenNameFieldLength > R3OutputFileConstants.GivenNameMaxLength)
            {
                givenNameFinal = givenNameFinal.Substring(0, R3OutputFileConstants.GivenNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (givenNameFieldLength < R3OutputFileConstants.GivenNameMaxLength)
            {
                int difference = R3OutputFileConstants.GivenNameMaxLength - givenNameFieldLength;
                givenNameFinal = givenNameFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return givenNameFinal;
        }
    }
}
