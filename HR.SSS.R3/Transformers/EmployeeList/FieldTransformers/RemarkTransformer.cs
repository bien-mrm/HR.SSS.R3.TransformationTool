using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class RemarkTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string Remark;

        public RemarkTransformer(string remark) : base(remark)
        {
            this.Remark = remark;
        }

        public string TransformField()
        {
            // Convert to uppercase
            string remarkFinal = this.Remark != null ? this.Remark.ToUpper() : "";
            var remarkFieldLength = this.Remark != null ? this.Remark.Length : 0;


            // Truncate if more than max length
            if (remarkFieldLength > OutputFileConstants.RemarkMaxLength)
            {
                remarkFinal = remarkFinal.Substring(0, OutputFileConstants.RemarkMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (remarkFieldLength < OutputFileConstants.RemarkMaxLength)
            {
                int difference = OutputFileConstants.RemarkMaxLength - remarkFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(remarkFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                remarkFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ remarkFinal } ";
        }
    }
}
