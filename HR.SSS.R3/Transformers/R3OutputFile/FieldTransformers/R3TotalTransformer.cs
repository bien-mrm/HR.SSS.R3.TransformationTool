using HR.SSS.R3.Constants;
using HR.SSS.R3.Models;
using HR.SSS.R3.Transformers.Abstracts;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Transformers.R3OutputFile.StringTransformers;
using HR.SSS.R3.Utilities;
using System.Text;

namespace HR.SSS.R3.Transformers.EmployeeList.FieldTransformers
{
    class R3TotalTransformer : FieldTransformer<R3SessionContainer>, IFieldTransformable
    {
        private readonly R3SessionContainer R3Session;

        public R3TotalTransformer(R3SessionContainer r3session) : base(r3session)
        {
            this.R3Session = r3session;
        }

        public string TransformField()
        {
            StringBuilder sb = new StringBuilder();

            sb.Append(R3OutputFileConstants.Numbers.LinePrepend99.AddSpace(8));

            R3AmountTransformer r3AmountTransformer = new R3AmountTransformer();
            sb.Append(r3AmountTransformer.TransformString(8));
            sb.Append(r3AmountTransformer.TransformString(4));
            sb.Append($"{ R3Session.TotalAmount }{ R3OutputFileConstants.Numbers.Extension00 }");
            sb.Append(r3AmountTransformer.TransformString(6));
            sb.Append(r3AmountTransformer.TransformString(6));
            sb.Append(r3AmountTransformer.TransformString(6));
            sb.Append(r3AmountTransformer.TransformString(6));
            sb.Append(r3AmountTransformer.TransformString(4));
            sb.Append($"{ R3Session.TotalEcAmount }{ R3OutputFileConstants.Numbers.Extension00 }");
            sb.Append(SpaceProvider.AddSpace(20));

            return sb.ToString();
        }
    }
}
