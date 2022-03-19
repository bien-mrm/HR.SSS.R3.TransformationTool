using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELDateHiredTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string DateHired;

        public ELDateHiredTransformer(string dateHired) : base(dateHired)
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
