using HR.SSS.R3.Constants;
using HR.SSS.R3.Models;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using System;
using System.Text;

namespace HR.SSS.R3.Extractors.R3OutputFile.FieldTransformers
{
    public class TransactionCodeTransformer : FieldTransformer<R3SessionContainer>, IFieldTransformable
    {
        private readonly R3SessionContainer R3Session;

        public TransactionCodeTransformer(R3SessionContainer r3Session) : base(r3Session)
        {
            this.R3Session = r3Session;
        }

        /** Format:
         - Applicable Period [MMyyyy] ("122021") +
         - Employer Number [10 characters] ("0395218183") +
         - TR Number [first 10 characters out of 15] ("0220309088") +
         - Date Today [MMddyyyy] ("02222022") +
         - Total Amount [9 minimum characters, right-aligned, if less than 9, prepend 0s] ("003140665")
         - Extension (".00") 
         */
        public string TransformField()
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(R3Session.ApplicablePeriod);
            sb.Append(R3Session.EmployerNumber);
            sb.Append(R3Session.TransactionNumber.Substring(0, R3OutputFileConstants.TransactionNumberMaxLength));
            sb.Append(DateTime.Now.ToString("MMddyyyy"));
            sb.Append(TransformTotalAmount());
            sb.Append(R3OutputFileConstants.Numbers.Extension00);

            return sb.ToString();
        }

        private string TransformTotalAmount()
        {
            string totalAmount = R3Session.TotalAmount.ToString();
            int totalAmountLength = totalAmount.Length;

            if (totalAmountLength < R3OutputFileConstants.TotalAmountMaxLength)
            {
                int difference = R3OutputFileConstants.TotalAmountMaxLength - totalAmountLength;

                for (int i = 0; i < difference; i++)
                {
                    totalAmount = "0" + totalAmount;
                }
            }

            return totalAmount;
        }
    }
}
