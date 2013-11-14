#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service2fc5b96b-5255-4d1e-a1fa-24c66995cb38" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service2fc5b96b-5255-4d1e-a1fa-24c66995cb38::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2fc5b96b-5255-4d1e-a1fa-24c66995cb38.war]", :immediately
end

ruby_block "remove-service2fc5b96b-5255-4d1e-a1fa-24c66995cb38" do
  block do
  	node.run_list.remove("recipe[service2fc5b96b-5255-4d1e-a1fa-24c66995cb38::war]")
  end
  only_if { node.run_list.include?("recipe[service2fc5b96b-5255-4d1e-a1fa-24c66995cb38::war]") }
end

ruby_block "remove-deactivate-service2fc5b96b-5255-4d1e-a1fa-24c66995cb38" do
  block do
    node.run_list.remove("recipe[deactivate-service2fc5b96b-5255-4d1e-a1fa-24c66995cb38]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2fc5b96b-5255-4d1e-a1fa-24c66995cb38]") }
end
