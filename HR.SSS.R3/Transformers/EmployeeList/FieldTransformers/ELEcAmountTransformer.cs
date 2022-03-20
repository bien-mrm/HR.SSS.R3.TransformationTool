﻿using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELEcAmountTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string EcAmount;

        public ELEcAmountTransformer(string ecAmount) : base(ecAmount)
        {
            this.EcAmount = ecAmount;
        }

        public string TransformField()
        {
            string ecAmountFinal = $"{ this.EcAmount }.00";
            var ecAmountFieldLength = ecAmountFinal.Length;

            // Truncate if more than max length
            if (ecAmountFieldLength > EmployeeListConstants.EcAmountMaxLength)
            {
                ecAmountFinal = ecAmountFinal.Substring(0, EmployeeListConstants.EcAmountMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (ecAmountFieldLength < EmployeeListConstants.EcAmountMaxLength)
            {
                int difference = EmployeeListConstants.EcAmountMaxLength - ecAmountFieldLength;
                ecAmountFinal = ecAmountFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ ecAmountFinal } ";
        }
    }
}
