using HR.SSS.R3.Constants;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    public class EmployerNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string EmployerName;

        public EmployerNameTransformer(string employerName) : base(employerName)
        {
            this.EmployerName = employerName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string employerName = this.EmployerName.ToUpper();
            var fieldCount = this.EmployerName.Length;

            // Truncate if more than max length
            if (fieldCount > OutputFileConstants.EmployerNameMaxLength)
            {
                employerName = employerName.Substring(0, OutputFileConstants.EmployerNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (fieldCount < OutputFileConstants.EmployerNameMaxLength)
            {
                int difference = OutputFileConstants.EmployerNameMaxLength - fieldCount;
                StringBuilder sb = new StringBuilder();
                sb.Append(employerName);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                employerName = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ employerName.ReplaceNcharacters() } ";
        }
    }
}
