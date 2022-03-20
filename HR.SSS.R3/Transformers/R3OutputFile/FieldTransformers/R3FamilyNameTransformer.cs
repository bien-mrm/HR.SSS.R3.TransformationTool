using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.R3OutputFile.FieldTransformers
{
    class R3FamilyNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string FamilyName;

        public R3FamilyNameTransformer(string familyName) : base(familyName)
        {
            this.FamilyName = familyName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string familyNameFinal = this.FamilyName.ReplaceNcharacters().SplitCamelCase().ToUpper();
            var familyNameFieldLength = familyNameFinal.Length;

            // Truncate if more than max length
            if (familyNameFieldLength > R3OutputFileConstants.FamilyNameMaxLength)
            {
                familyNameFinal = familyNameFinal.Substring(0, R3OutputFileConstants.FamilyNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (familyNameFieldLength < R3OutputFileConstants.FamilyNameMaxLength)
            {
                int difference = R3OutputFileConstants.FamilyNameMaxLength - familyNameFieldLength;
                familyNameFinal = familyNameFinal.AddSpace(difference);
            }

            return familyNameFinal;
        }
    }
}
