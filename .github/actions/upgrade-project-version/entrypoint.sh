function upgrade_project_version() {
    PROJECT_VERSION=$(cat ./pom.xml | grep "<version>" | head -1 | awk -F'>' '{ print $2 }' | sed "s/[<\/version ]//g")
    GITHUB_SHORT_SHA=$(echo ${GITHUB_SHA} | cut -c1-8)

    echo "Project version: ${PROJECT_VERSION}"
    echo "Github short SHA: ${GITHUB_SHORT_SHA}"

    case "$CI_COMMIT_REF_NAME" in
      master)
        sed -i -e "0,/\(<version>\)/s/\(<version>.*\)\(<\/version>\)/\1-FINAL\2/" pom.xml
        ;;
      *)
        sed -i -e "0,/\(<version>\)/s/\(<version>.*\)\(<\/version>\)/\1-${GITHUB_SHORT_SHA}\2/" pom.xml
        ;;
    esac

    cat pom.xml
}

upgrade_project_version()