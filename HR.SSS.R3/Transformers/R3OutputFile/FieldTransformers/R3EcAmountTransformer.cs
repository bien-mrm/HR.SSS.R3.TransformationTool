using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class R3EcAmountTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string EcAmount;

        public R3EcAmountTransformer(string ecAmount) : base(ecAmount)
        {
            this.EcAmount = ecAmount;
        }

        public string TransformField()
        {
            string ecAmountFinal = $"{ this.EcAmount }{ R3OutputFileConstants.Numbers.Extension00}";
            var ecAmountFieldLength = ecAmountFinal.Length;

            // Truncate if more than max length
            if (ecAmountFieldLength > R3OutputFileConstants.EcAmountMaxLength)
            {
                ecAmountFinal = ecAmountFinal.Substring(0, R3OutputFileConstants.EcAmountMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (ecAmountFieldLength < R3OutputFileConstants.EcAmountMaxLength)
            {
                int difference = R3OutputFileConstants.EcAmountMaxLength - ecAmountFieldLength;
                ecAmountFinal = ecAmountFinal.AddSpace(difference);
            }

            return ecAmountFinal;
        }
    }
}
