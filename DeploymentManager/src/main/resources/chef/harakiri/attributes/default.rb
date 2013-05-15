##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of $NAME must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Because there is possibility of more than one DeploymentManager
default['CHOReOSData']['nodeData']['deploymentManagerURL'] = "$DEPLOYMENT_MANAGER_URL"

# The id used to send delete request on resource 
# http://deploymentmanagerhost:port/nodes/<node_id>
default['CHOReOSData']['nodeData']['nodeID'] = "$NODE_ID"