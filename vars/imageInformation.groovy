def call(String image, String tag ) {
    sh """
    echo "=================================================="
    echo "       ABC BANKING PRODUCTION DEPLOYMENT"
    echo "=================================================="
    echo "Host            : \$(hostname)"
    echo "Deployment AT   : \$(date)"
    echo "Application     : abc-banking"
    echo "Image           : ${image}:${tag}"
    echo "==================================================="
    
    """
}