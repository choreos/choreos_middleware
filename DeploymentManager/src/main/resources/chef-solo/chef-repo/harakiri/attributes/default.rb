##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# Ocurrences of $DEPLOYMENT_MANAGER_URL and $NODE_ID must be replaced    #
#      with the deployment manager url and the id of this node in EE     #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Because there is possibility of more than one DeploymentManager
default['CHOReOSData']['nodeData']['deploymentManagerURL'] = "$DEPLOYMENT_MANAGER_URL"

# The id used to send delete request on resource 
# http://deploymentmanagerhost:port/nodes/<node_id>
default['CHOReOSData']['nodeData']['nodeID'] = "$NODE_ID"