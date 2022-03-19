using HR.SSS.R3.Transformers.Interfaces;
using System;

namespace HR.SSS.R3.Transformers.EmployeeList.StringTransformers
{
    public class ELDateTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return $"Date: { DateTime.Now.ToString("MMM dd, yyyy") }";
        }
    }
}
