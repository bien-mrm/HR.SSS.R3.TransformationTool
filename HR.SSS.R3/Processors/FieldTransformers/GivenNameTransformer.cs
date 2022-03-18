using HR.SSS.R3.Constants;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using System.Text;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    class GivenNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string GivenName;

        public GivenNameTransformer(string givenName) : base(givenName)
        {
            this.GivenName = givenName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string givenNameFinal = this.GivenName.ToUpper();
            var givenNameFieldLength = this.GivenName.Length;


            // Truncate if more than max length
            if (givenNameFieldLength > OutputFileConstants.MiddleInitialMaxLength)
            {
                givenNameFinal = givenNameFinal.Substring(0, OutputFileConstants.MiddleInitialMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (givenNameFieldLength < OutputFileConstants.MiddleInitialMaxLength)
            {
                int difference = OutputFileConstants.MiddleInitialMaxLength - givenNameFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(givenNameFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                givenNameFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ givenNameFinal } ";
        }
    }
}
