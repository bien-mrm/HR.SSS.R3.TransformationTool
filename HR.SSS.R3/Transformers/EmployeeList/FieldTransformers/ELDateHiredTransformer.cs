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
            return this.DateHired != null ? this.DateHired.ToUpper() : "";
        }
    }
}
