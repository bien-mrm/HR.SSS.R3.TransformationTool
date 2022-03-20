﻿using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELSssContributionTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string SssContribution;

        public ELSssContributionTransformer(string sssContribution) : base(sssContribution)
        {
            this.SssContribution = sssContribution;
        }

        public string TransformField()
        {
            string sssContributionFinal = $"{ this.SssContribution }.00";
            var sssContributionFieldLength = sssContributionFinal.Length;


            // Truncate if more than max length
            if (sssContributionFieldLength > EmployeeListConstants.SssContributionMaxLength)
            {
                sssContributionFinal = sssContributionFinal.Substring(0, EmployeeListConstants.SssContributionMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (sssContributionFieldLength < EmployeeListConstants.SssContributionMaxLength)
            {
                int difference = EmployeeListConstants.SssContributionMaxLength - sssContributionFieldLength;
                sssContributionFinal = sssContributionFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ sssContributionFinal } ";
        }
    }
}
