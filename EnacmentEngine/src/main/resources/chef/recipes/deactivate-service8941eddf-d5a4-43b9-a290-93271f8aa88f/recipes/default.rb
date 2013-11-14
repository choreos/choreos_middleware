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

ruby_block "remove-service8941eddf-d5a4-43b9-a290-93271f8aa88f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service8941eddf-d5a4-43b9-a290-93271f8aa88f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/8941eddf-d5a4-43b9-a290-93271f8aa88f.war]", :immediately
end

ruby_block "remove-service8941eddf-d5a4-43b9-a290-93271f8aa88f" do
  block do
  	node.run_list.remove("recipe[service8941eddf-d5a4-43b9-a290-93271f8aa88f::war]")
  end
  only_if { node.run_list.include?("recipe[service8941eddf-d5a4-43b9-a290-93271f8aa88f::war]") }
end

ruby_block "remove-deactivate-service8941eddf-d5a4-43b9-a290-93271f8aa88f" do
  block do
    node.run_list.remove("recipe[deactivate-service8941eddf-d5a4-43b9-a290-93271f8aa88f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8941eddf-d5a4-43b9-a290-93271f8aa88f]") }
end
