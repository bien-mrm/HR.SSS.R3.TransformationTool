using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELRemarkTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string Remark;

        public ELRemarkTransformer(string remark) : base(remark)
        {
            this.Remark = remark;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string remarkFinal = this.Remark != null ? this.Remark.ToUpper() : "";
            var remarkFieldLength = this.Remark != null ? this.Remark.Length : 0;


            // Truncate if more than max length
            if (remarkFieldLength > EmployeeListConstants.RemarkMaxLength)
            {
                remarkFinal = remarkFinal.Substring(0, EmployeeListConstants.RemarkMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (remarkFieldLength < EmployeeListConstants.RemarkMaxLength)
            {
                int difference = EmployeeListConstants.RemarkMaxLength - remarkFieldLength;
                remarkFinal = remarkFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ remarkFinal } ";
        }
    }
}
