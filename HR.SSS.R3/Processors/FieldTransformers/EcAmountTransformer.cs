using HR.SSS.R3.Constants;
using HR.SSS.R3.Processors.Abstracts;
using HR.SSS.R3.Processors.Interfaces;
using System.Text;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    class EcAmountTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string EcAmount;

        public EcAmountTransformer(string ecAmount) : base(ecAmount)
        {
            this.EcAmount = ecAmount;
        }

        public string TransformField()
        {
            string emAmountFinal = $"{ this.EcAmount }.00";
            var ecAmountFieldLength = emAmountFinal.Length;


            // Truncate if more than max length
            if (ecAmountFieldLength > OutputFileConstants.EcAmountMaxLength)
            {
                emAmountFinal = emAmountFinal.Substring(0, OutputFileConstants.EcAmountMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (ecAmountFieldLength < OutputFileConstants.EcAmountMaxLength)
            {
                int difference = OutputFileConstants.EcAmountMaxLength - ecAmountFieldLength;
                StringBuilder sb = new StringBuilder();
                sb.Append(emAmountFinal);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                emAmountFinal = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ emAmountFinal } ";
        }
    }
}
