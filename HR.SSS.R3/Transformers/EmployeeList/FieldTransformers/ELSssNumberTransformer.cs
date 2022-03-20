﻿using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class ELSssNumberTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string SssNumber;

        public ELSssNumberTransformer(string sssNumber) : base(sssNumber)
        {
            this.SssNumber = sssNumber;
        }

        public string TransformField()
        {
            if (this.SssNumber == null)
            {
                return SpaceProvider.AddSpace(15);
            }

            // Convert to uppercase
            string sssNumberFinal = this.SssNumber.Length == 9 ? $"0{ this.SssNumber }" : this.SssNumber; 
            sssNumberFinal = sssNumberFinal.Insert(2, "-").Insert(10, "-");
            var sssNumberFieldLength = sssNumberFinal.Length;

            // Truncate if more than max length
            if (sssNumberFieldLength > EmployeeListConstants.SssNumberMaxLength)
            {
                sssNumberFinal = sssNumberFinal.Substring(0, EmployeeListConstants.SssNumberMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (sssNumberFieldLength < EmployeeListConstants.SssNumberMaxLength)
            {
                int difference = EmployeeListConstants.SssNumberMaxLength - sssNumberFieldLength;
                sssNumberFinal = sssNumberFinal.AddSpace(difference);
            }

            // Remember to add an extra space as column separation
            return $"{ sssNumberFinal } ";
        }
    }
}
