using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    public class ELEmployerNameTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string EmployerName;

        public ELEmployerNameTransformer(string employerName) : base(employerName)
        {
            this.EmployerName = employerName;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string employerName = this.EmployerName.ToUpper();
            var fieldCount = this.EmployerName.Length;

            // Truncate if more than max length
            if (fieldCount > EmployeeListConstants.EmployerNameMaxLength)
            {
                employerName = employerName.Substring(0, EmployeeListConstants.EmployerNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (fieldCount < EmployeeListConstants.EmployerNameMaxLength)
            {
                int difference = EmployeeListConstants.EmployerNameMaxLength - fieldCount;
                employerName = employerName.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ employerName.ReplaceNcharacters() } ";
        }
    }
}
