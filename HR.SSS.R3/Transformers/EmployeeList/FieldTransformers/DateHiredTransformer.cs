using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class DateHiredTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string DateHired;

        public DateHiredTransformer(string dateHired) : base(dateHired)
        {
            this.DateHired = dateHired;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string dateHiredFinal = this.DateHired != null ? this.DateHired.ToUpper() : "";

            // Remember to add an extra space as column separation
            return $"{ dateHiredFinal }";
        }
    }
}
