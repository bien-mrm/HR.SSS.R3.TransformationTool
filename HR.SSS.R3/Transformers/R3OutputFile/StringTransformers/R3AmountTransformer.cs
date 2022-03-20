using HR.SSS.R3.Constants;
using HR.SSS.R3.Utilities;

namespace HR.SSS.R3.Transformers.R3OutputFile.StringTransformers
{
    public class R3AmountTransformer
    {
        public string TransformString(int numberOfSpaces)
        {
            return R3OutputFileConstants.Numbers.ZeroAmount.AddSpace(numberOfSpaces);
        }
    }
}
