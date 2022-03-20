using HR.SSS.R3.Constants;
using HR.SSS.R3.Transformers.Interfaces;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Transformers.R3OutputFile.StringTransformers
{
    public class R3NormalTransformer : IStringTransformable
    {
        public string TransformString()
        {
            return R3OutputFileConstants.NormalDateHired.AddSpace(7);
        }
    }
}
