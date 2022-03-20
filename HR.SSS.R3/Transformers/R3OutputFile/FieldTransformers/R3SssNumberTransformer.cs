using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class R3SssNumberTransformer : FieldTransformer<string>, IFieldTransformable
    {
        private readonly string SssNumber;

        public R3SssNumberTransformer(string sssNumber) : base(sssNumber)
        {
            this.SssNumber = sssNumber;
        }

        public string TransformField()
        {
            if (this.SssNumber == null)
            {
                // 14 characters, spaces included
                return SpaceProvider.AddSpace(14);
            }

            return this.SssNumber;
        }
    }
}
