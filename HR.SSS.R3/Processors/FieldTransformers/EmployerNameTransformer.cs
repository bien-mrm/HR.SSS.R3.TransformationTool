﻿using HR.SSS.R3.Constants;
using HR.SSS.R3.Models;
using HR.SSS.R3.Processors.Interfaces;
using System.Text;

namespace HR.SSS.R3.Processors.FieldTransformers
{
    public class EmployerNameTransformer : IFieldTransformer
    {
        public string TransformField(R3SessionContainer r3Session)
        {
            // Convert to uppercase
            string employerName = r3Session.EmployerName.ToUpper();
            var fieldCount = r3Session.EmployerName.Length;

            // Truncate if more than max length
            if (fieldCount > OutputFileConstants.EmployerNameMaxLength)
            {
                employerName = employerName.Substring(0, OutputFileConstants.EmployerNameMaxLength);
            }

            // If less than max length, add spaces until max length
            else if (fieldCount < OutputFileConstants.EmployerNameMaxLength)
            {
                int difference = OutputFileConstants.EmployerNameMaxLength - fieldCount;
                StringBuilder sb = new StringBuilder();
                sb.Append(employerName);

                for (int i = 0; i < difference; i++)
                {
                    sb.Append(" ");
                }

                employerName = sb.ToString();
            }

            // Remember to add an extra space as column separation
            return $"{ employerName } ";
        }
    }
}