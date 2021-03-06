using HR.SSS.R3.Constants;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    class MiddleInitialTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string MiddleInitial;

        public MiddleInitialTransformer(string middleInitial) : base(middleInitial)
        {
            this.MiddleInitial = middleInitial;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string middleInitialFinal = this.MiddleInitial != null ? this.MiddleInitial.ToUpper() : "";
            var middleInitialFieldLength = this.MiddleInitial != null ? this.MiddleInitial.Length : 0;


            // Truncate if more than max length
            if (middleInitialFieldLength > OutputFileConstants.MiddleInitialMaxLength)
            {
                middleInitialFinal = middleInitialFinal.Substring(0, OutputFileConstants.MiddleInitialMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (middleInitialFieldLength < OutputFileConstants.MiddleInitialMaxLength)
            {
                int difference = OutputFileConstants.MiddleInitialMaxLength - middleInitialFieldLength;
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
